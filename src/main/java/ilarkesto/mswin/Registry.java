package ilarkesto.mswin;

import ilarkesto.base.Proc;

import java.util.StringTokenizer;

public class Registry {

    public static final String HKCU = "HKCU";
    public static final String HKCU_ENVIRONMENT = "HKEY_CURRENT_USER\\Environment";

    public static void main(String[] args) {
        appendToUsersPath("e:\\daten\\a101zi8");
    }

    public static void appendToUsersPath(String additionalPath) {
        additionalPath = additionalPath.replace('/', '\\');
        String path = getString(HKCU_ENVIRONMENT, "PATH");
        if (path != null && path.indexOf(additionalPath) >= 0) return;
        if (!additionalPath.endsWith(";")) additionalPath += ";";
        if (path == null) path = "%PATH%;";
        if (!path.endsWith(";")) path += ";";
        path += additionalPath;
        setString(HKCU_ENVIRONMENT, "PATH", path);
    }

    public static void setString(String key, String string, String value) {
        Proc proc = new Proc("REG");
        proc.addParameter("ADD");
        proc.addParameter(key);
        proc.addParameter("/f");
        proc.addParameter("/v");
        proc.addParameter(string);
        proc.addParameter("/d");
        proc.addParameter(value);
        proc.start();
        int ret = proc.getReturnCode();
        String output = proc.getOutput();
        if (ret != 0) throw new RuntimeException("Command failed: " + output);
    }

    public static String getString(String key, String string) {
        Proc proc = new Proc("REG");
        proc.addParameter("QUERY");
        proc.addParameter(key);
        proc.addParameter("/v");
        proc.addParameter(string);
        proc.start();
        int ret = proc.getReturnCode();
        String output = proc.getOutput();
        if (ret == 1) return null;
        if (ret != 0) throw new RuntimeException("Command failed: " + output);
        StringTokenizer tokenizer = new StringTokenizer(output, "\n\r");
        String valueLine = null;
        while (tokenizer.hasMoreTokens()) {
            String tok = tokenizer.nextToken().trim();
            if (key.equals(tok)) {
                valueLine = tokenizer.nextToken().trim();
                break;
            }
        }
        if (valueLine == null) { throw new RuntimeException("Parsing command output failed: " + output); }
        valueLine = valueLine.substring(string.length() + 1);
        int idx = valueLine.indexOf('\t') + 1;
        valueLine = valueLine.substring(idx);
        return valueLine;
    }

}
