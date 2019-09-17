package main.java.utils;

import java.io.BufferedInputStream;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.io.File;
import org.apache.commons.io.IOUtils;

public class PropUtils {

    public static Properties loadAllProperties(File propDir)throws Exception{
        FileFilter fileFilter = new FileFilter() {

            public boolean accept(File f)
            {
                return f.getName().endsWith("properties");
            }
        };
        Properties props = new Properties();
        return loadAllProperties(propDir, fileFilter, props);
    }

    private static Properties loadAllProperties(File propDir, FileFilter fileFilter, Properties props) throws Exception{
        BufferedInputStream inp = null;
        File[] fileArr = propDir.isDirectory() ? propDir.listFiles() : new File[]{propDir};
        List<File> files = Arrays.asList(fileArr);
        Collections.sort(files, (File o1, File o2) -> {
            return o1.getName().compareTo(o2.getName());
        });
        for (File f : files) {
            if (f.isDirectory()) {// recursively load all folder
                loadAllProperties(f,fileFilter,props);
                continue;
            }
            System.out.println("Loading env props from " + f.getAbsolutePath());
            try {
                inp = new BufferedInputStream(new FileInputStream(f));
                props.load(inp);
            } finally {
                IOUtils.closeQuietly(inp);
            }
        }
        return props;
    }
}
