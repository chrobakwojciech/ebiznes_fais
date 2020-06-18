import React, {useEffect, useState} from "react";

import {orderApi} from "../../utils/api/order.api";
import UserOrders from "./UserOrders";
import Grid from "@material-ui/core/Grid";
import EditUser from "./EditUser";
import UserInfo from "./UserInfo";

export default function UserProfile() {
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const orders = await orderApi.getUserOrders();
            setOrders(orders);
        };

        fetchData();
    }, []);

    return (
        <>
            <Grid container direction="row" spacing={4}>
                <Grid item xs={7}>
                    <UserOrders orders={orders}/>
                </Grid>
                <Grid item xs={3}>
                    <UserInfo/>
                    <EditUser/>
                </Grid>
            </Grid>
        </>
    )
}
