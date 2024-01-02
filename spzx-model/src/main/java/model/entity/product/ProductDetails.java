package model.entity.product;


import lombok.Data;
import model.entity.base.BaseEntity;

@Data
public class ProductDetails extends BaseEntity {

	private Long productId;
	private String imageUrls;

}