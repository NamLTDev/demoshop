$(document).ready(function(){
	$("#BtnAdd2Cart").on("click",function(e) {
		addToCart();
	});
});

function addToCart(){
	quantity = $("#quantity" + productId).val();
	
	url = contextPath + "cart/add/" + productId + "/" + quantity;
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName,csrfValue);
		}
	}).done(function(response) {
			alert("Đã thêm vào giỏ");
		}).fail(function() {
			alert("Fail");
		})
}