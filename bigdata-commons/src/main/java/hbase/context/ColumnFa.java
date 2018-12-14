package hbase.context;

/**
 * @author zxfcode
 * @create 2018-10-26 15:14
 */
public enum ColumnFa {
    CF_INFO("info"),CF_ATTENDS("attends"),CF_FANS("fans"),CF_CONTENT("content");
    private String colName;
    private ColumnFa(String colName){
        this.colName = colName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }
}
