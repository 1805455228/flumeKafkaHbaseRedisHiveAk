package hbase.context;

/**
 * @author zxfcode
 * @create 2018-10-26 13:08
 */
public enum NameTable {
    NAMESPACE_ATGUIGU("atguigu"),TABLE_WEIBO("atguigu:weibo"),TABLE_INBOX("atguigu:inbox"),TABLE_RELATIONS("atguigu:relation");

    private String name;
    private NameTable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
