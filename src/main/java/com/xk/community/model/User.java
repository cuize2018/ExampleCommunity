package com.xk.community.model;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertable.id
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertable.name
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertable.account_id
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    private String account_id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertable.token
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertable.gmt_create
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    private Long gmt_create;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertable.gmt_modified
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    private Long gmt_modified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertable.bio
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    private String bio;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertable.avatar_url
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    private String avatar_url;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertable.id
     *
     * @return the value of usertable.id
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertable.id
     *
     * @param id the value for usertable.id
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertable.name
     *
     * @return the value of usertable.name
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertable.name
     *
     * @param name the value for usertable.name
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertable.account_id
     *
     * @return the value of usertable.account_id
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public String getAccount_id() {
        return account_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertable.account_id
     *
     * @param account_id the value for usertable.account_id
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public void setAccount_id(String account_id) {
        this.account_id = account_id == null ? null : account_id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertable.token
     *
     * @return the value of usertable.token
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertable.token
     *
     * @param token the value for usertable.token
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertable.gmt_create
     *
     * @return the value of usertable.gmt_create
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public Long getGmt_create() {
        return gmt_create;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertable.gmt_create
     *
     * @param gmt_create the value for usertable.gmt_create
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public void setGmt_create(Long gmt_create) {
        this.gmt_create = gmt_create;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertable.gmt_modified
     *
     * @return the value of usertable.gmt_modified
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public Long getGmt_modified() {
        return gmt_modified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertable.gmt_modified
     *
     * @param gmt_modified the value for usertable.gmt_modified
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public void setGmt_modified(Long gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertable.bio
     *
     * @return the value of usertable.bio
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public String getBio() {
        return bio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertable.bio
     *
     * @param bio the value for usertable.bio
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public void setBio(String bio) {
        this.bio = bio == null ? null : bio.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertable.avatar_url
     *
     * @return the value of usertable.avatar_url
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public String getAvatar_url() {
        return avatar_url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertable.avatar_url
     *
     * @param avatar_url the value for usertable.avatar_url
     *
     * @mbg.generated Fri Mar 06 15:34:24 CST 2020
     */
    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url == null ? null : avatar_url.trim();
    }
}