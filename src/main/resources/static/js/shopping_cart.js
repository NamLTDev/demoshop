$(document).ready(function(){
	updateTotal();
	
	$(".cart_quantity_down").on("click", function(evt){
		evt.preventDefault();
		decreaseQuantity($(this));
	});
	
	$(".cart_quantity_up").on("click", function(evt){
		evt.preventDefault();
		increaseQuantity($(this));
	});
	
	$(".link-remove").on("click",function(evt){
		evt.preventDefault();
		removeFromCart($(this));
	});
});

function removeFromCart(link){
	url = link.attr("href");

	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName,csrfValue);
		}
	}).done(function(response) {
			alert("Xóa sản phẩm thành công!");
			rowNumber = link.attr("rowNumber");
			removeProduct(rowNumber);
			updateTotal();
		}).fail(function() {
			alert("Fail");
		})
}

function removeProduct(rowNumber){
	rowId = "row" + rowNumber;
	$("#" + rowId).remove();
}

function decreaseQuantity(link){
	productId = link.attr("pid");
	qtyInput = $("#quantity" + productId);
	
	newQty = parseInt(qtyInput.val()) -1;
	if(newQty > 0) {
		qtyInput.val(newQty);
		updateQuantity(productId, newQty);
	}
}

function increaseQuantity(link){
		productId = link.attr("pid");
		qtyInput = $("#quantity" + productId);
		
		newQty = parseInt(qtyInput.val()) + 1;
		if(newQty < 10){ 
			qtyInput.val(newQty);
			updateQuantity(productId, newQty);
		}
}

function updateQuantity(productId, quantity){
	url = contextPath + "cart/update/" + productId + "/" + quantity;
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName,csrfValue);
		}
	}).done(function(newSubtotal) {
			updateSubtotal(newSubtotal, productId);
			updateTotal();
		}).fail(function() {
			alert("Fail");
		})
	
}

function updateSubtotal(newSubtotal, productId){
	$("#subtotal" + productId).text(newSubtotal);
}

function updateTotal(){
	total = 0.0;
	
	$(".cart_total_price").each(function (index, element){
		total = total + parseFloat(element.innerHTML)
	});
	
	$("#totalAmount").text(total + "VND");
}