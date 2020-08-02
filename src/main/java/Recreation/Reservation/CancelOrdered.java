package Recreation.Reservation;

public class CancelOrdered extends AbstractEvent {

    private Long id;
    private String Status;
    private Long RoomId;
    private String RoomName;
    private Long CustomerId;
    private String OrderDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    public Long getRoomId() {
        return RoomId;
    }

    public void setRoomId(Long RoomId) {
        this.RoomId = RoomId;
    }
    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String RoomName) {
        this.RoomName = RoomName;
    }
    public Long getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(Long CustomerId) {
        this.CustomerId = CustomerId;
    }
    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }
}