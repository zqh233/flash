package com.flash.dataObject;

import com.flash.service.model.UserModel;

public class UserPasswordDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_password.id
     *
     * @mbg.generated Wed Jan 16 16:58:21 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_password.encrpt
     *
     * @mbg.generated Wed Jan 16 16:58:21 CST 2019
     */
    private String encrpt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_password.user_id
     *
     * @mbg.generated Wed Jan 16 16:58:21 CST 2019
     */
    private Integer userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_password.id
     *
     * @return the value of user_password.id
     *
     * @mbg.generated Wed Jan 16 16:58:21 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_password.id
     *
     * @param id the value for user_password.id
     *
     * @mbg.generated Wed Jan 16 16:58:21 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_password.encrpt
     *
     * @return the value of user_password.encrpt
     *
     * @mbg.generated Wed Jan 16 16:58:21 CST 2019
     */
    public String getEncrpt() {
        return encrpt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_password.encrpt
     *
     * @param encrpt the value for user_password.encrpt
     *
     * @mbg.generated Wed Jan 16 16:58:21 CST 2019
     */
    public void setEncrpt(String encrpt) {
        this.encrpt = encrpt == null ? null : encrpt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_password.user_id
     *
     * @return the value of user_password.user_id
     *
     * @mbg.generated Wed Jan 16 16:58:21 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_password.user_id
     *
     * @param userId the value for user_password.user_id
     *
     * @mbg.generated Wed Jan 16 16:58:21 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        if(userModel == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrpt(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }
}