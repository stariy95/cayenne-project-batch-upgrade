package aa.bb.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.List;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.property.DateProperty;
import org.apache.cayenne.exp.property.ListProperty;
import org.apache.cayenne.exp.property.PropertyFactory;
import org.apache.cayenne.exp.property.StringProperty;

import aa.bb.Items;
import aa.bb.Painting;

/**
 * Class _Orders was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Orders extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final DateProperty<LocalDate> ORDER_DATE = PropertyFactory.createDate("orderDate", LocalDate.class);
    public static final StringProperty<String> ORDER_STATUS = PropertyFactory.createString("orderStatus", String.class);
    public static final ListProperty<Items> ITEMS = PropertyFactory.createList("items", Items.class);
    public static final ListProperty<Painting> PAINTINGS = PropertyFactory.createList("paintings", Painting.class);

    protected LocalDate orderDate;
    protected String orderStatus;

    protected Object items;
    protected Object paintings;

    public void setOrderDate(LocalDate orderDate) {
        beforePropertyWrite("orderDate", this.orderDate, orderDate);
        this.orderDate = orderDate;
    }

    public LocalDate getOrderDate() {
        beforePropertyRead("orderDate");
        return this.orderDate;
    }

    public void setOrderStatus(String orderStatus) {
        beforePropertyWrite("orderStatus", this.orderStatus, orderStatus);
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        beforePropertyRead("orderStatus");
        return this.orderStatus;
    }

    public void addToItems(Items obj) {
        addToManyTarget("items", obj, true);
    }

    public void removeFromItems(Items obj) {
        removeToManyTarget("items", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<Items> getItems() {
        return (List<Items>)readProperty("items");
    }

    public void addToPaintings(Painting obj) {
        addToManyTarget("paintings", obj, true);
    }

    public void removeFromPaintings(Painting obj) {
        removeToManyTarget("paintings", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<Painting> getPaintings() {
        return (List<Painting>)readProperty("paintings");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "orderDate":
                return this.orderDate;
            case "orderStatus":
                return this.orderStatus;
            case "items":
                return this.items;
            case "paintings":
                return this.paintings;
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
            case "orderDate":
                this.orderDate = (LocalDate)val;
                break;
            case "orderStatus":
                this.orderStatus = (String)val;
                break;
            case "items":
                this.items = val;
                break;
            case "paintings":
                this.paintings = val;
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
        out.writeObject(this.orderDate);
        out.writeObject(this.orderStatus);
        out.writeObject(this.items);
        out.writeObject(this.paintings);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.orderDate = (LocalDate)in.readObject();
        this.orderStatus = (String)in.readObject();
        this.items = in.readObject();
        this.paintings = in.readObject();
    }

}
