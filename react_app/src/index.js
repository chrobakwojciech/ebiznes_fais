import React from 'react';
import ReactDOM from 'react-dom';
import CssBaseline from '@material-ui/core/CssBaseline';
import {ThemeProvider} from '@material-ui/core/styles';
import theme from './theme';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Home from "./containers/Home";
import Movies from "./containers/Movies";
import Layout from "./components/Layout";

const routing = (
    <ThemeProvider theme={theme}>
        <CssBaseline/>
        <BrowserRouter>
            <Layout>
                <Switch >
                    <Route exact path="/"><Home/></Route>
                    <Route path="/movies"><Movies/></Route>
                </Switch>
            </Layout>

        </BrowserRouter>
    </ThemeProvider>
);

ReactDOM.render(routing, document.querySelector('#root'));