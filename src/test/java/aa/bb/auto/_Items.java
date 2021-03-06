package aa.bb.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.property.ListProperty;
import org.apache.cayenne.exp.property.PropertyFactory;
import org.apache.cayenne.exp.property.StringProperty;

import aa.bb.Orders;

/**
 * Class _Items was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Items extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final StringProperty<String> ITEM_DESCR = PropertyFactory.createString("itemDescr", String.class);
    public static final StringProperty<String> ITEM_NAME = PropertyFactory.createString("itemName", String.class);
    public static final ListProperty<Orders> ORDERSS = PropertyFactory.createList("orderss", Orders.class);

    protected String itemDescr;
    protected String itemName;

    protected Object orderss;

    public void setItemDescr(String itemDescr) {
        beforePropertyWrite("itemDescr", this.itemDescr, itemDescr);
        this.itemDescr = itemDescr;
    }

    public String getItemDescr() {
        beforePropertyRead("itemDescr");
        return this.itemDescr;
    }

    public void setItemName(String itemName) {
        beforePropertyWrite("itemName", this.itemName, itemName);
        this.itemName = itemName;
    }

    public String getItemName() {
        beforePropertyRead("itemName");
        return this.itemName;
    }

    public void addToOrderss(Orders obj) {
        addToManyTarget("orderss", obj, true);
    }

    public void removeFromOrderss(Orders obj) {
        removeToManyTarget("orderss", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<Orders> getOrderss() {
        return (List<Orders>)readProperty("orderss");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "itemDescr":
                return this.itemDescr;
            case "itemName":
                return this.itemName;
            case "orderss":
                return this.orderss;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "itemDescr":
                this.itemDescr = (String)val;
                break;
            case "itemName":
                this.itemName = (String)val;
                break;
            case "orderss":
                this.orderss = val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.itemDescr);
        out.writeObject(this.itemName);
        out.writeObject(this.orderss);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.itemDescr = (String)in.readObject();
        this.itemName = (String)in.readObject();
        this.orderss = in.readObject();
    }

}
