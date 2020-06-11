import React from 'react';
import ReactDOM from 'react-dom';
import CssBaseline from '@material-ui/core/CssBaseline';
import {ThemeProvider} from '@material-ui/core/styles';
import theme from './theme';
import {BrowserRouter, Switch} from "react-router-dom";
import MovieGrid from "./components/movies/grid/MovieGrid";
import Movie from "./components/movies/item/Movie";
import BaseLayout from "./layout/base/BaseLayout";
import AuthLayout from "./layout/AuthLayout";
import AppRoute from "./utils/AppRoute";
import Login from "./components/auth/Login";
import SignUp from "./components/auth/SignUp";
import UserContextProvider from "./context/UserContextProvider";

const routing = (
    <ThemeProvider theme={theme}>
        <CssBaseline/>
        <UserContextProvider>
            <BrowserRouter>
                <Switch>
                    <AppRoute exact path="/" component={MovieGrid} layout={BaseLayout}/>
                    <AppRoute path="/gatunek/:genreName" component={MovieGrid} layout={BaseLayout}/>
                    <AppRoute path="/filmy/:movieId" component={Movie} layout={BaseLayout}/>

                    <AppRoute exact path="/logowanie" component={Login} layout={AuthLayout}/>
                    <AppRoute exact path="/rejestracja" component={SignUp} layout={AuthLayout}/>
                </Switch>
            </BrowserRouter>
        </UserContextProvider>
    </ThemeProvider>
);

ReactDOM.render(routing, document.querySelector('#root'));