package slimevoid.lib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JSParser {
	
	public static Object parse(String script, String func, Map<String,Object> params) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine se = mgr.getEngineByName("JavaScript");
		
		try {
			for ( String param: params.keySet() ) {
				se.put(param, params.get(param));
			}
			
			se.eval(script);
			return se.eval(func);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return null;
	}
}
