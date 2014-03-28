/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package net.slimevoid.library.util.javascript;

import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.slimevoid.library.core.SlimevoidCore;
import net.slimevoid.library.core.lib.CoreLib;

public class JSParser {

    public static Object parse(String script, String func, Map<String, Object> params) {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine se = mgr.getEngineByName("JavaScript");

        try {
            for (String param : params.keySet()) {
                se.put(param,
                       params.get(param));
            }

            se.eval(script);
            return se.eval(func);
        } catch (ScriptException e) {
            SlimevoidCore.console(CoreLib.MOD_ID,
                                  e.getLocalizedMessage());
        }
        return null;
    }
}
