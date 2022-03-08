$(document).ready(function(){
	$(".cart_quantity_down").on("click", function(evt){
		evt.preventDefault();
		productId = $(this).attr("pid");
		qtyInput = $("#quantity" + productId);
		newQty = parseInt(qtyInput.val()) - 1;
		if(newQty > 0) qtyInput.val(newQty);
	});
	
	$(".cart_quantity_up").on("click", function(evt){
		evt.preventDefault();
		productId = $(this).attr("pid");
		qtyInput = $("#quantity" + productId);
		newQty = parseInt(qtyInput.val()) + 1;
		if(newQty < 10) qtyInput.val(newQty);
	});
	
});