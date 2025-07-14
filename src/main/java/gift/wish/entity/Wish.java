package gift.wish.entity;

public class Wish {
    Long id;
    Long productId;
    Long memberId;
    int quantity;

    public Wish(Long productId, Long memberId, int quantity) {
        this.id = null;
        this.productId = productId;
        this.memberId = memberId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public int getQuantity() {
        return quantity;
    }
}
