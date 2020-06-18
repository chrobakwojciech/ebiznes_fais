import API from "./API";

class OrderApi {
    async getPaymentMethods() {
        let payments = [];
        try {
            payments = await API.get('/payments');
            payments = payments.data;
        } catch (e) {
            console.log(e);
        }
        return payments;
    }

    async makeOrder(payment, movies) {
        if (movies.length === 0) {
            throw new Error()
        }
        await API.post('/orders', { payment, movies })
    }

    async getUserOrders() {
        let orders = [];
        try {
            orders = await API.get('/user/orders');
            orders = orders.data;
        } catch (e) {
            console.log(e);
        }
        return orders;
    }
}

export const orderApi = new OrderApi();
