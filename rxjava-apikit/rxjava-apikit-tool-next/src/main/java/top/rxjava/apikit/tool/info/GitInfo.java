package top.rxjava.apikit.tool.info;

import lombok.Data;

@Data
public class GitInfo {
    /**
     * user.name
     */
    private String name;
    /**
     * user.email
     */
    private String email;
    private String url;
    private String branch = "master";
}
