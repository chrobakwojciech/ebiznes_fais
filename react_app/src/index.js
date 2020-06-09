import React from 'react';
import ReactDOM from 'react-dom';
import CssBaseline from '@material-ui/core/CssBaseline';
import {ThemeProvider} from '@material-ui/core/styles';
import theme from './theme';
import {BrowserRouter, Switch} from "react-router-dom";
import MovieGrid from "./components/movies/MovieGrid";
import Movie from "./components/movies/Movie";
import Layout from "./layout/BaseLayout";
import AuthLayout from "./layout/AuthLayout";
import AppRoute from "./AppRoute";
import Login from "./components/auth/Login";
import SignUp from "./components/auth/SignUp";

const routing = (
    <ThemeProvider theme={theme}>
        <CssBaseline/>
        <BrowserRouter>
            <Switch>
                <AppRoute exact path="/" component={MovieGrid} layout={Layout}/>
                <AppRoute path="/gatunek/:genreName" component={MovieGrid} layout={Layout}/>
                <AppRoute path="/filmy/:movieId" component={Movie} layout={Layout}/>
                <AppRoute exact path="/logowanie" component={Login} layout={AuthLayout}/>
                <AppRoute exact path="/rejestracja" component={SignUp} layout={AuthLayout}/>
            </Switch>
        </BrowserRouter>
    </ThemeProvider>
);

ReactDOM.render(routing, document.querySelector('#root'));