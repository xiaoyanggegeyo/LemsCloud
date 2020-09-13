package com.msgcloud.utils.file;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtil {
	//上传图片路径
	public final static String PATH = "";

	private final static int xhdpi = 660;
	private final static int mhdpi = 1280;

	private final static String[] imgtypes = {"jpg", "jpeg", "png", "gif"};
	private final static String[] wordtypes = {"doc", "docx"};
	private final static String[] exceltypes = {"xls", "xlsx"};
	private final static String[] pdftypes = {"pdf"};
	private final static String[] vediotypes = {"mp4"};
	private final static String[] apptypes = {"apk"};
	private final static String[] pdfziptypes = {"zip"};

	private final static String FILENAME_PATTERN = "[\u4e00-\u9fa50-9A-Za-z-_.]{1,100}";

	/**
	 * 判断文件名称是否合法
	 */
	public static boolean filenameMacth(String filename) {
		return Pattern.matches(FILENAME_PATTERN, filename);
	}

	public static Boolean checkFileType(String type, String fileName) {
		String fileType = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : "";

		ArrayList<String> typelist = new ArrayList<>();
		if (type.equals("image")) {
			typelist = new ArrayList<>(Arrays.asList(imgtypes));
		}
		if (type.equals("word")) {
			typelist = new ArrayList<>(Arrays.asList(wordtypes));
		}
		if (type.equals("excel")) {
			typelist = new ArrayList<>(Arrays.asList(exceltypes));
		}
		if (type.equals("pdf")) {
			typelist = new ArrayList<>(Arrays.asList(pdftypes));
		}
		if (type.equals("zip")) {
			typelist = new ArrayList<>(Arrays.asList(pdfziptypes));
		}
		if (type.equals("vedio")) {
			typelist = new ArrayList<>(Arrays.asList(vediotypes));
		}
		if (type.equals("wxapp")) {
			typelist = new ArrayList<>(Arrays.asList(apptypes));
		}

		return typelist.contains(fileType);
	}

	public static String getTrueFilename(String fileName) {
		//判断是否为IE浏览器的文件名，IE浏览器下文件名会带有盘符信息
		// Check for Unix-style path
		int unixSep = fileName.lastIndexOf('/');
		// Check for Windows-style path
		int winSep = fileName.lastIndexOf('\\');
		// Cut off at latest possible point
		int pos = (winSep > unixSep ? winSep : unixSep);
		if (pos != -1) {
			// Any sort of path separator found...
			fileName = fileName.substring(pos + 1);
		}

		return fileName;
	}

	/**
	 * 上传图片，以文件的形式上传，对图片进行压缩，会生成多个分辨率的图片
	 *
	 * @param file     文件数据
	 * @param filePath 文件的存储路径
	 * @param fileName 生成的文件名
	 * @throws Exception
	 */
	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		String fileType = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : "";

		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + PATH + "/" + fileName);
		out.write(file);
		out.close();
		System.out.println(filePath + PATH + "/" + fileName);
		String upNameXhdpi = "xhdpi-" + fileName;
		String upNameMhdpi = "mhdpi-" + fileName;

		FileOutputStream outXhdpi = new FileOutputStream(filePath + "/" + PATH + "/" + upNameXhdpi);
		FileOutputStream outMhdpi = new FileOutputStream(filePath + "/" + PATH + "/" + upNameMhdpi);

		resizeImageFile(file, outXhdpi, xhdpi, fileType);
		resizeImageFile(file, outMhdpi, mhdpi, fileType);
	}

	public static boolean uploadPdfZipFile(byte[] file, String filePath, String fileName, String destPath) throws Exception {
		//上传zip文件
		String fileType = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : "";
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + "/" + fileName);
		out.write(file);
		out.close();

		//解压文件
		ZipInputStream Zin = new ZipInputStream(new FileInputStream(
				filePath + fileName));//输入源zip路径
		BufferedInputStream Bin = new BufferedInputStream(Zin);
		String Parent = destPath; //输出路径（文件夹目录）
		File Fout = null;
		ZipEntry entry;
		try {
			while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
				if (!filenameMacth(entry.getName())) {
					continue;
				}

				Fout = new File(Parent, entry.getName());
				if (!Fout.exists()) {
					(new File(Fout.getParent())).mkdirs();
				}
				FileOutputStream outpdf = new FileOutputStream(Fout);
				BufferedOutputStream Bout = new BufferedOutputStream(outpdf);
				int b;
				while ((b = Bin.read()) != -1) {
					Bout.write(b);
				}
				Bout.close();
				outpdf.close();
			}
			Bin.close();
			Zin.close();

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void uploadWordExcel(byte[] file, String filePath, String fileName) throws Exception {
		String fileType = fileName.substring(fileName.length() - 3, fileName.length());
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + PATH + "/" + fileName);
		out.write(file);
		out.close();
		System.out.println(filePath + PATH + "/" + fileName);
	}

	/**
	 * 上传文件，以文件的形式，不会对图片进行压缩
	 *
	 * @param file         要上传的文件
	 * @param fileFileName 文件名
	 * @return 上传成功后的文件名
	 */
	public static String upload(HttpServletRequest request, File file, String fileFileName) throws Exception {
//        String dir ="/upload/avatar/provision/file/";
//        File fileLocation = new File(dir);
//        if(!fileLocation.exists()) {
//            boolean isCreated  = fileLocation.mkdir();
//            if(!isCreated) {
//                //目标上传目录创建失败,可做其他处理,例如抛出自定义异常等,一般应该不会出现这种情况。
//                return null;
//            }
//        }
//        fileFileName = UUID.randomUUID().toString()+System.currentTimeMillis()+fileFileName;
//
//        InputStream is = new FileInputStream(file);
//
//        OutputStream os = new FileOutputStream(dir+"/"+fileFileName);
//
//
//
//        // 因为file是存放在临时文件夹的文件，我们可以将其文件名和文件路径打印出来，看和之前的fileFileName是否相同
//
//        byte[] buffer = new byte[500];
//        int length = 0;
//
//        while(-1 != (length = is.read(buffer, 0, buffer.length)))
//        {
//            os.write(buffer);
//        }
//
//        os.close();
//        is.close();
//
//        return fileFileName;
		return "";
	}


	/**
	 * 将网络资源的图片下载到本地，比如说将微信用户头像下载到本地
	 *
	 * @param request
	 * @param fileUrl 网络资源的路径
	 * @return
	 */
	public static String uploadQianURL(HttpServletRequest request, String fileUrl) {
		//获取文件名，文件名实际上在URL中可以找到
		String fileName = UUID.randomUUID().toString() + System.currentTimeMillis() + ".jpg";
		//这里服务器上要将此图保存的路径
		String savePath = request.getServletContext().getRealPath(PATH);
		System.out.println();
		try {
			URL url = new URL(fileUrl);/*将网络资源地址传给,即赋值给url*/
			/*此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流*/
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			DataInputStream in = new DataInputStream(connection.getInputStream());
			/*此处也可用BufferedInputStream与BufferedOutputStream*/
			DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath + "\\" + fileName));
			/*将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址*/
			byte[] buffer = new byte[4096];
			int count = 0;
			/*将输入流以字节的形式读取并写入buffer中*/
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.close();/*后面三行为关闭输入输出流以及网络资源的固定格式*/
			in.close();
			connection.disconnect();
			//返回内容是文件名
			return fileName;/*网络资源截取并存储本地成功返回true*/

		} catch (Exception e) {
			System.out.println(e + fileUrl + savePath);
			return null;
		}
	}

	/**
	 * 上传图片，以base64的形式
	 *
	 * @param base64 图片编码后的字符串
	 * @return 上传成功后的文件名
	 */
	public static String upload(HttpServletRequest request, String base64) {
		String dir = request.getServletContext().getRealPath(PATH);
		File fileLocation = new File(dir);
		if (!fileLocation.exists()) {
			boolean isCreated = fileLocation.mkdir();
			if (!isCreated) {
				//目标上传目录创建失败,可做其他处理,例如抛出自定义异常等,一般应该不会出现这种情况。
				return null;
			}
		}
		if (base64.indexOf("jpeg") != -1) {
			//存在jpeg文件的情况
			base64 = base64.replaceFirst("jpeg", "jpg");
		}
		String upName = UUID.randomUUID().toString() + System.currentTimeMillis() + "." + base64.substring(11, 14);
		FileOutputStream out;
		String iconBase64 = base64.substring(22);
		try {
			byte[] buffer = new BASE64Decoder().decodeBuffer(iconBase64);
			out = new FileOutputStream(dir + "/" + upName);
			out.write(buffer);
			out.close();
			return upName;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 上传图片，会生成多个分辨率的图片
	 *
	 * @param base64 图片编码后的字符串
	 * @return 上传成功后的文件名
	 */
	public static String uploadResize(HttpServletRequest request, String base64) {
		String dir = request.getServletContext().getRealPath(PATH);
		if (base64.indexOf("jpeg") != -1) {
			//存在jpeg文件的情况
			base64 = base64.replaceFirst("jpeg", "jpg");
		}
		File fileLocation = new File(dir);
		String fileType = base64.substring(11, 14);
		if (!fileLocation.exists()) {
			boolean isCreated = fileLocation.mkdir();
			if (!isCreated) {
				//目标上传目录创建失败,可做其他处理,例如抛出自定义异常等,一般应该不会出现这种情况。
				return null;
			}
		}

		String tempStr = UUID.randomUUID().toString() + System.currentTimeMillis();

		String upName = tempStr + "." + fileType;
		FileOutputStream out;
		String iconBase64 = base64.substring(22);
		try {
			byte[] buffer = new BASE64Decoder().decodeBuffer(iconBase64);
			out = new FileOutputStream(dir + "/" + upName);
			out.write(buffer);
			out.close();

			String upNameXhdpi = "xhdpi-" + upName;
			String upNameMhdpi = "mhdpi-" + upName;

			FileOutputStream outXhdpi = new FileOutputStream(dir + "/" + upNameXhdpi);
			FileOutputStream outMhdpi = new FileOutputStream(dir + "/" + upNameMhdpi);

			resizeImage(iconBase64, outXhdpi, xhdpi, fileType);
			resizeImage(iconBase64, outMhdpi, mhdpi, fileType);

			return upName;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 改变图片的大小到宽为size，然后高随着宽等比例变化
	 *
	 * @param is     上传的图片的输入流
	 * @param os     改变了图片的大小后，把图片的流输出到目标OutputStream
	 * @param size   新图片的宽
	 * @param format 新图片的格式
	 * @throws IOException
	 */
	public static void resizeImage(String is, OutputStream os, int size, String format) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(is));
		ByteArrayInputStream in2 = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(is));
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in2.read(b)) != -1; ) {
			out.append(new String(b, 0, n));
		}
		System.out.println(out.length());
		BufferedImage prevImage = ImageIO.read(in);
		double width = prevImage.getWidth();
		double height = prevImage.getHeight();
		double percent = size / width;
		int newWidth = (int) (width * percent);
		int newHeight = (int) (height * percent);
		BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
		Graphics graphics = image.createGraphics();
		graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);
		ImageIO.write(image, format, os);
		os.flush();
		in.close();
		os.close();
	}

	/**
	 * 改变图片的大小到宽为size，然后高随着宽等比例变化
	 *
	 * @param file   上传的图片的输入流
	 * @param os     改变了图片的大小后，把图片的流输出到目标OutputStream
	 * @param size   新图片的宽
	 * @param format 新图片的格式
	 * @throws IOException
	 */
	public static void resizeImageFile(byte[] file, OutputStream os, int size, String format) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(file);
		ByteArrayInputStream in2 = new ByteArrayInputStream(file);
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in2.read(b)) != -1; ) {
			out.append(new String(b, 0, n));
		}
		System.out.println(out.length());
		BufferedImage prevImage = ImageIO.read(in);
		double width = prevImage.getWidth();
		double height = prevImage.getHeight();
		double percent = size / width;
		int newWidth = (int) (width * percent);
		int newHeight = (int) (height * percent);
		BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
		Graphics graphics = image.createGraphics();
		graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);
		ImageIO.write(image, format, os);
		os.flush();
		in.close();
		os.close();
	}

	//文件夹
	public static void download(String path, HttpServletResponse response) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			if (file.exists() && file.isFile()) {
				// 取得文件名。
				String filename = file.getName();
				// 取得文件的后缀名。
//                String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

				// 以流的形式下载文件。
				InputStream fis = new BufferedInputStream(new FileInputStream(path));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				// 清空response
				response.reset();
				// 设置response的Header
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
				response.addHeader("Content-Length", "" + file.length());
				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void uploadNormalFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + PATH + "/" + fileName);
		out.write(file);
		out.close();
	}

	public static void deleteIfExists(File file) throws IOException {
		if (file.exists()) {
			if (file.isFile()) {
				if (!file.delete()) {
					throw new IOException("Delete file failure,path:" + file.getAbsolutePath());
				}
			} else {
				File[] files = file.listFiles();
				if (files != null && files.length > 0) {
					for (File temp : files) {
						deleteIfExists(temp);
					}
				}
				if (!file.delete()) {
					throw new IOException("Delete file failure,path:" + file.getAbsolutePath());
				}
			}
		}
	}


}
