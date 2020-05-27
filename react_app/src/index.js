import React from 'react';
import ReactDOM from 'react-dom';
import CssBaseline from '@material-ui/core/CssBaseline';
import {ThemeProvider} from '@material-ui/core/styles';
import theme from './theme';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import NavBar from "./components/Navbar";
import Home from "./containers/Home";
import Movies from "./containers/Movies";

const routing = (
    <ThemeProvider theme={theme}>
        <CssBaseline/>
        <BrowserRouter>
            <NavBar/>
            <Switch >
                <Route exact path="/"><Home/></Route>
                <Route path="/movies"><Movies/></Route>
            </Switch>
        </BrowserRouter>
    </ThemeProvider>
);

ReactDOM.render(routing, document.querySelector('#root'));