package com.github.bogeymanest.execscript;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.LogMode;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestExecscript {
    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog(LogMode.LOG_ONLY);
    @Parameterized.Parameter
    public String file;

    @Parameterized.Parameters(name = "{index}: file({0})")
    public static Collection<Object> files() {
        return Arrays.asList(new Object[][]{
                {"vars"},
                {"interpolation"},
                {"concmds"},
                {"controlflow"},
                {"comments"},
        });
    }

    /**
     * Compiles the given source file and asserts that the contents of the output
     * file is equal to the contents of the expected file
     *
     * @param source   The source file resource name
     * @param expected The expected output file resource name
     */
    public void compileAssert(String source, String expected) {
        InputStream srcInput = TestExecscript.class.getResourceAsStream(source);
        InputStream expInput = TestExecscript.class.getResourceAsStream(expected);
        log.clear();

        try {
            String exp = IOUtils.toString(expInput, "UTF-8");
            Execscript.compile(srcInput, System.out);
            assertEquals(exp, log.getLog());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFile() throws Exception {
        compileAssert(file + ".txt", file + "_out.txt");
    }
}
