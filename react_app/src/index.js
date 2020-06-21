import React from 'react';
import ReactDOM from 'react-dom';
import CssBaseline from '@material-ui/core/CssBaseline';
import {ThemeProvider} from '@material-ui/core/styles';
import theme from './theme';
import {BrowserRouter, Redirect, Switch} from "react-router-dom";
import Movie from "./components/movies/item/Movie";
import BaseLayout from "./layout/base/BaseLayout";
import AuthLayout from "./layout/AuthLayout";
import AppRoute from "./utils/AppRoute";
import Login from "./components/auth/Login";
import SignUp from "./components/auth/SignUp";
import UserContextProvider from "./context/userContext/UserContextProvider";
import GenreMovies from "./components/genres/GenreMovies";
import UserLibrary from "./components/user/UserLibrary";
import ActorMovies from "./components/actors/ActorMovies";
import NotFound from "./layout/NotFound";
import BasketContextProvider from "./context/basketContext/BasketContextProvider";
import Basket from "./components/basket/Basket";
import UserProfile from "./components/user/UserProfile";
import DirectorMovies from "./components/directors/DirectorMovies";
import AllMovies from "./components/movies/grid/AllMovies";
import GoogleAuth from "./components/auth/Google";

const routing = (
    <ThemeProvider theme={theme}>
        <CssBaseline/>
        <UserContextProvider>
            <BasketContextProvider>
                <BrowserRouter>
                    <Switch>
                        <AppRoute exact path="/" component={AllMovies} layout={BaseLayout}/>
                        <AppRoute path="/gatunek/:genreName" component={GenreMovies} layout={BaseLayout}/>
                        <AppRoute path="/filmy/:movieId" component={Movie} layout={BaseLayout}/>
                        <AppRoute path="/aktor/:actorId" component={ActorMovies} layout={BaseLayout}/>
                        <AppRoute path="/rezyser/:directorId" component={DirectorMovies} layout={BaseLayout}/>
                        <AppRoute path="/biblioteka" component={UserLibrary} layout={BaseLayout}/>
                        <AppRoute path="/koszyk" component={Basket} layout={BaseLayout}/>
                        <AppRoute path="/profil" component={UserProfile} layout={BaseLayout}/>

                        <AppRoute exact path="/logowanie" component={Login} layout={AuthLayout}/>
                        <AppRoute exact path="/rejestracja" component={SignUp} layout={AuthLayout}/>
                        <AppRoute exact path="/oauth/google" component={GoogleAuth} layout={AuthLayout}/>
                        <AppRoute exact path="/404" component={NotFound} layout={AuthLayout}/>
                        <Redirect to="/404"/>
                    </Switch>
                </BrowserRouter>
            </BasketContextProvider>
        </UserContextProvider>
    </ThemeProvider>
);

ReactDOM.render(routing, document.querySelector('#root'));
