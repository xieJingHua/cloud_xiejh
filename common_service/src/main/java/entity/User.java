package entity;

import lombok.Data;

/**
 * @author xiejh
 * @Date 2020/10/10 16:44
 **/
@Data
public class User {

    private Integer id;

    private String code;

    private String name;

    public User(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
