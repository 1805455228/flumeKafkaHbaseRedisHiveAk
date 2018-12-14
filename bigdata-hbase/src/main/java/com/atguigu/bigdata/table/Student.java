package com.atguigu.bigdata.table;

/**
 * @author zxfcode
 * @create 2018-10-24 10:07
 */
@TableRef("student1")
public class Student {
    @Rowkey
    private String rowkey;
    @ColumnRef(column = "name")
    private String name;
    private String age;
    private String email;
    @ColumnRef(columnFamily = "detail")
    private String tel;
    private String adress;

    public String getRowkey() {
        return rowkey;
    }

    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
