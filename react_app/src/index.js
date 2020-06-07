import React from 'react';
import ReactDOM from 'react-dom';
import CssBaseline from '@material-ui/core/CssBaseline';
import {ThemeProvider} from '@material-ui/core/styles';
import theme from './theme';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Movies from "./containers/Movies";
import Movie from "./containers/Movie";
import Layout from "./components/Layout";

const routing = (
    <ThemeProvider theme={theme}>
        <CssBaseline/>
        <BrowserRouter>
            <Layout>
                <Switch >
                    <Route exact path="/"><Movies/></Route>
                    <Route path="/gatunek/:genreName" render={(props) => (
                        <Movies key={props.match.params.genreName}/>
                    )}/>              
                    <Route path="/filmy/:movieId" render={(props) => (
                        <Movie key={props.match.params.movieId}/>
                    )}/>
                </Switch>
            </Layout>

        </BrowserRouter>
    </ThemeProvider>
);

ReactDOM.render(routing, document.querySelector('#root'));