import api, { authService } from "./api";

if (!localStorage.getItem("cart")) {
    localStorage.setItem("cart", JSON.stringify([]));
}

async function addToCartLocal(ProductObject) {
    if (authService.isLogged()) return;
    let cart = JSON.parse(localStorage.getItem("cart")) ?? [];
    const index = cart.findIndex(item => item.id === ProductObject.id);
    if (index !== -1) {
        cart[index].quantity += 1;
    } else {
        cart.push({ ...ProductObject, quantity: 1 });
    }

    localStorage.setItem("cart", JSON.stringify(cart));
}
async function syncLocalCartToServer() {
    if (!authService.isLogged()) return;
    let cart = JSON.parse(localStorage.getItem("cart")) ?? [];
    if (cart.length === 0) return;
    try {
        await api.post('/addToCart', cart);
        localStorage.setItem("cart", JSON.stringify([]));
    } catch (error) {
        console.error("Failed to sync cart:", error);
    }
}
