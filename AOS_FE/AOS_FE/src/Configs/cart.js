import api, { authService } from "./api";

// {
//     "id": "",
//         "qty": "2",
//             "createdAt": "",
//                 "updatedAt": "",
//                     "accounts": "2",
//                         "productItems": "4"
// }

if (!localStorage.getItem("cart")) {
    localStorage.setItem("cart", JSON.stringify([]));
}

async function addToCartLocal(ProductObject) {
    if (authService.isLogged()) return;
    let cart = JSON.parse(localStorage.getItem("cart")) ?? [];
    // console.log(cart.value)
    const index = cart.findIndex(item => item.productItems === ProductObject.productItems);
    if (index !== -1) {
        cart[index].qty += ProductObject.qty;
    } else {
        cart.push({ ...ProductObject });
    }

    localStorage.setItem("cart", JSON.stringify(cart));
}
async function syncLocalCartToServer() {

    console.debug(!authService.isLogged());
    if (!authService.isLogged()) return;
    let cart = JSON.parse(localStorage.getItem("cart")) ?? [];

    console.debug(!authService.isLogged(), cart);
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
    if (!authService.isLogged()) return;
    try {
        itemCart.accounts = authService.getUserName();
        await api.post('/addToCart', itemCart);

    } catch (error) {
        console.error("Failed to sync cart:", error);
    }
}
async function finalHandleCartProgress(itemCart) {
    if (!authService.isLogged()) {
        addToCartLocal(itemCart)
    } else {
        handleCartWhileLogin(itemCart)
    }
}
export { syncLocalCartToServer, finalHandleCartProgress }
