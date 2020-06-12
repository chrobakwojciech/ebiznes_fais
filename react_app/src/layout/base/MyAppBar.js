import AppBar from "@material-ui/core/AppBar/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import {Link, useHistory} from "react-router-dom";
import React, {useContext} from "react";
import {makeStyles} from "@material-ui/core/styles";
import {UserContext} from "../../context/userContext/UserContext";
import IconButton from "@material-ui/core/IconButton";
import Badge from "@material-ui/core/Badge";
import MailIcon from '@material-ui/icons/Mail';
import {BasketContext} from "../../context/basketContext/BasketContext";
import {ShoppingCart} from "@material-ui/icons";
import AppBarBasket from "./AppBarBasket";


const useStyles = makeStyles((theme) => ({
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
    },
    title: {
        flexGrow: 1
    }
}));


export default function MyAppBar() {
    const classes = useStyles();
    const {userCtx, setUserCtx} = useContext(UserContext);
    const {getBasketMovies} = useContext(BasketContext);

    let history = useHistory();

    const logOutHandler = () => {
        setUserCtx({user: null, token: null});
        localStorage.removeItem('userCtx');
        history.push('/');
    };

    const UserInfoPanel = () => {
        if (userCtx.user) {
            return (
                <>
                    <Typography variant="button">{userCtx.user.firstName} {userCtx.user.lastName}</Typography>
                    <AppBarBasket/>
                    <Button color="inherit" onClick={logOutHandler}>Wyloguj się</Button>
                </>
            )
        } else {
            return (
                <>
                    <AppBarBasket/>
                    <Button color="inherit" component={Link} to="/logowanie">Zaloguj się</Button>
                </>
            )
        }
    };

    return (
        <AppBar position="fixed" className={classes.appBar}>
            <Toolbar>
                <Typography className={classes.title} variant="h6" noWrap>MovieVOD</Typography>
                <UserInfoPanel/>
            </Toolbar>
        </AppBar>
    )
}