package com.prabhash.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Calendar;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
@Provider
public class MyDateConverterProvider implements ParamConverterProvider {

	@Override
	public <T> ParamConverter<T> getConverter(final Class<T> rawtype, Type arg1, Annotation[] arg2) {
		
		if(rawtype.getName().equals(MyDate.class.getName())){
			
			return new ParamConverter<T>() {

				@Override
				public T fromString(String value) {
				
					Calendar cal = Calendar.getInstance();
					
					if("tomorrow".equalsIgnoreCase(value)){
						
						cal.add(Calendar.DATE, 1);
					}else if("yesterday".equalsIgnoreCase(value)){
						
						cal.add(Calendar.DATE,-1);
					}
					
					MyDate mydate = new MyDate();
					
					mydate.setDate(cal.get(Calendar.DATE));
					mydate.setMonth(cal.get(Calendar.MONTH));
					mydate.setYear(cal.get(Calendar.YEAR));
					
					return rawtype.cast(mydate);
				}

				@Override
				public String toString(T arg0) {
					
					if(arg0==null){
						
						return null;
					}
					return arg0.toString();
				}
			};
			
		}
				
		return null;
	}

}
