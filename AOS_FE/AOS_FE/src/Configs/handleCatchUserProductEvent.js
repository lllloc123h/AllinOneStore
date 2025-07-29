import api, { authService } from "./api";
import platform from 'platform';
const deviceInfo = {
    name: platform.name,             // Browser name (e.g. Chrome)
    version: platform.version,       // Browser version
    layout: platform.layout,         // Rendering engine (e.g. Blink, WebKit)
    os: platform.os?.toString(),     // Operating system (e.g. Windows 10)
    description: platform.description, // Full string description
    product: platform.product        // Device type (e.g. iPad, iPhone)
};
async function catchUserEvent(itemCart) {

    try {
        const ipRes = await fetch('https://api.ipify.org?format=json');
        const ipData = await ipRes.json();

        let payLoad = {
            id: '',
            eventType: itemCart.eventType,
            eventTime: new Date().toISOString(),
            sessionId: '',
            device: deviceInfo.product,
            ipAddress: ipData.ip,
            userAgent: navigator.userAgent,
            positionInList: '',
            timeSpentSeconds: itemCart.timeSpentSeconds,
            referrer: document.referrer,
            accounts: '',
            productItems: itemCart.productItemId,
        }
        if (authService.isLogged()) {
            payLoad.accounts = authService.getUserName();
        } else {
            payLoad.accounts = "AdminToCountUserCatchEvent";

        }
        const response = await api.post('/UserProductEvents', payLoad);
    } catch (error) {
        console.error("Failed to catch user event:", error);
    }
}

export { catchUserEvent }
