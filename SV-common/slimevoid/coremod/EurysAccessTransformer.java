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
package slimevoid.coremod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

public class EurysAccessTransformer extends AccessTransformer {
    private static EurysAccessTransformer instance;
    private static List<String> mapFiles = new LinkedList<String>();
    public EurysAccessTransformer() throws IOException {
            super();
            instance = this;
            // add access transformers here
            mapFiles.add("slimevoid.cfg");
            Iterator<String> it = mapFiles.iterator();
            while (it.hasNext()) {
                    String file = it.next();
                    this.readMapFile(file);
            }
            
    }
    public static void addTransformerMap(String mapFileName) {
            if (instance == null) {
                    mapFiles.add(mapFileName);
            }
            else {
                    instance.readMapFile(mapFileName);
            }
    }
    private void readMapFile(String name) {
            System.out.println("Adding transformer map: " + name);
            try {
                    // get a method from AccessTransformer
                    Method e = AccessTransformer.class.getDeclaredMethod("readMapFile", new Class[]{String.class});
                    e.setAccessible(true);
                    // run it with the file given.
                    e.invoke(this, new Object[]{name});
                    
            }
            catch (Exception ex) {
                    throw new RuntimeException(ex);
            }
    }
}
