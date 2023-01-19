package controller;

public class PathController {
    
    private static final String ROOT_PATH = ".";
    private static final String SLASH = "/";

    private String path = ROOT_PATH;

    public String getCurrentPath() {
        return path;
    }

    public boolean isRoot() {
        return path.equals(ROOT_PATH);
    }

    public boolean tryGoUp() {
        if (isRoot()) {
            return false;
        }

        var index = path.lastIndexOf(SLASH);
        path = path.substring(0, index);
        return true;
    }

    public void goToDirectory(String name) {
        path += SLASH + name;
    }
}
