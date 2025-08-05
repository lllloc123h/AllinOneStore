import api, { authService } from "./api";

if (!localStorage.getItem("cart")) {
    localStorage.setItem("cart", JSON.stringify([]));
}

async function addToCartLocal(ProductObject) {
    if (authService.isLogged()) return;
    let cart = JSON.parse(localStorage.getItem("cart")) ?? [];
    const index = cart.findIndex(item => item.productItems === ProductObject.productItems);
    if (index !== -1) {
        cart[index].qty += ProductObject.qty;
    } else {
        cart.push({ ...ProductObject });
    }
    localStorage.setItem("cart", JSON.stringify(cart));
}
async function syncLocalCartToServer() {
    if (!authService.isLogged()) return;
    let cart = JSON.parse(localStorage.getItem("cart")) ?? [];
    if (cart.length === 0) return;
    try {
        for (const itemCart of cart) {
            itemCart.accounts = authService.getUserName();
            await api.post('/addToCart', itemCart);
        }
        localStorage.setItem("cart", JSON.stringify([]));
    } catch (error) {
        console.error("Failed to sync cart:", error);
    }
}

async function handleCartWhileLogin(itemCart) {
    try {
        itemCart.accounts = authService.getUserName();
        await api.post('/addToCart', itemCart);

    } catch (error) {
        console.error("Failed to sync cart:", error);
    }
}

async function handleUpdateQuantityCartWhileLogin(itemCart, updateType) {
    if (!authService.isLogged()) return;
    try {
        const itemUpdate = {
            id: itemCart.id,
            accounts: authService.getUserName(),
            productItems: itemCart.productItemId,
            qty: itemCart.quantity,
            promotions: itemCart.promotions || null,
            comboGroup: itemCart.comboGroup,
            comboQty: itemCart.comboQty,
            comboGroupId: itemCart.comboGroupId,
            name: itemCart.name,
            mainImageUrl: itemCart.mainImageUrl,
            price: itemCart.price,
            sku: itemCart.sku,
            createdAt: "",
            updatedAt: "",
        }
        await api.put(
            '/addToCart?updateType=' + updateType, itemUpdate
        )
    } catch (error) {
        console.error("Failed to sync cart:", error);
    }
}
async function finalHandleCartProgress(itemCart) {
        handleCartWhileLogin(itemCart)
}
export { syncLocalCartToServer, finalHandleCartProgress, handleUpdateQuantityCartWhileLogin }
