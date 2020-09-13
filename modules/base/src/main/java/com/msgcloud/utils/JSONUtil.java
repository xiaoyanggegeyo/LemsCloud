package com.msgcloud.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JSONUtil {

	private static ObjectMapper mapper;

	/**
	 * 获取ObjectMapper实例
	 * 
	 * @param createNew
	 *            方式：true，新实例；false,存在的mapper实例
	 * @return
	 */
	public static synchronized ObjectMapper getMapperInstance(boolean createNew) {
		if (createNew) {
			return new ObjectMapper();
		} else if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param obj
	 *            准备转换的对象
	 * @param createNew
	 *            ObjectMapper实例方式:true，新实例;false,存在的mapper实例
	 * @return json字符串
	 * @throws Exception
	 */
	public static String beanToJson(Object obj, Boolean createNew) throws Exception {
		return getMapperInstance(false).writeValueAsString(obj);
	}
	
	/**
	 * 将json对象转换为字符串，为null值的转成""
	 * @param obj
	 * @return
	 */
    public static String beanToJsonWithNull(Object obj) {  
        ObjectMapper mapper = getMapperInstance(false);  
        // null替换为""  
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {  
            @Override  
            public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {  
                arg1.writeString("");  
            }  
        });  
        String str = null;  
        try {  
            str = mapper.writeValueAsString(obj);  
        } catch (JsonGenerationException e) {  
            e.printStackTrace();  
        } catch (JsonMappingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return str;  
    }  

	/**
	 * 将json字符串转换成java对象
	 * 
	 * @param json
	 *            准备转换的json字符串
	 * @param cls
	 *            准备转换的类
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonToBean(String json, Class<T> cls) throws Exception {
		return getMapperInstance(false).readValue(json, cls);
	}
	
	/**
	 * 将json字符串转换成List对象
	 * 
	 * @param json
	 *            准备转换的json字符串
	 * @param cls
	 *            准备转换的泛型
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws Exception
	 */
	public static <T> List<T> jsonToList(String json, Class<T> cls) throws JsonParseException, JsonMappingException, IOException {
		JavaType javaType = getCollectionType(ArrayList.class, cls);
		return getMapperInstance(false).readValue(json, javaType);
	}
	
	/**
	 * 将json字符串转换成Set对象
	 * 
	 * @param json
	 *            准备转换的json字符串
	 * @param cls
	 *            准备转换的泛型
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws Exception
	 */
	public static <T> Set<T> jsonToSet(String json, Class<T> cls) throws JsonParseException, JsonMappingException, IOException {
		JavaType javaType = getCollectionType(HashSet.class, cls);
		return getMapperInstance(false).readValue(json, javaType);
	}
	
	/**
	 * 将json字符串转换成Map对象
	 * @param json 准备转换的json字符串
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(String json) throws JsonParseException, JsonMappingException, IOException {
        return getMapperInstance(false).readValue(json, Map.class);
	}
	
	/**
	 * 获取泛型的Collection Type
	 * 
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public static JavaType getCollectionType(Class<?> collectionClass,
			Class<?>... elementClasses) {
		return getMapperInstance(false).getTypeFactory().constructParametricType(collectionClass,
				elementClasses);
	}
	
	/**
	 * @Title: writeValue
	 * @Description: TODO(将对象转换为JSON流)
	 * @author masn
	 * @date 2018年1月23日 下午7:46:27
	 * @return void  返回类型
	 * @throws
	 */
	public static void writeValue(Writer writer, Object value) {
		try {
			mapper.writeValue(writer, value);
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}