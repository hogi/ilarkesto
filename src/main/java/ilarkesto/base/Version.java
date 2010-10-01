package ilarkesto.base;

import java.util.StringTokenizer;

/**
 * Data type for a version like 1.0.12
 */
public final class Version {

    private int major;
    private int minor;
    private int build;

    public Version(int major, int minor, int build) {
        this.major = major;
        this.minor = minor;
        this.build = build;
    }

    public Version(String s) {
        StringTokenizer tok = new StringTokenizer(s, ".");
        if (!tok.hasMoreElements()) return;
        major = Integer.parseInt(tok.nextToken());
        if (!tok.hasMoreElements()) return;
        minor = Integer.parseInt(tok.nextToken());
        if (!tok.hasMoreElements()) return;
        build = Integer.parseInt(tok.nextToken());
    }

    public Version(Version version) {
        this(version.major, version.minor, version.build);
    }

    public void incrementMajor() {
        major++;
    }

    public void incrementMinor() {
        minor++;
    }

    public void icrementBuild() {
        build++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(major);
        if (minor != 0 || build != 0) {
            sb.append('.').append(minor);
            if (build != 0) {
                sb.append('.').append(build);
            }
        }
        return sb.toString();
    }

}
