package com.lw.controller.viewObject;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 用户视图对象
 */
public class UserVO {

    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telephone;

    private String tempUsefulOtpCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTempUsefulOtpCode() {
        return tempUsefulOtpCode;
    }

    public void setTempUsefulOtpCode(String tempUsefulOtpCode) {
        this.tempUsefulOtpCode = tempUsefulOtpCode;
    }
}
