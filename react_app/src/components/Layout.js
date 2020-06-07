import React, {useEffect, useState} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import CssBaseline from '@material-ui/core/CssBaseline';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import {NavLink} from "react-router-dom";
import {Movie} from "@material-ui/icons";
import * as colors from "@material-ui/core/colors";
import API from "../utils/API";
import ListSubheader from "@material-ui/core/ListSubheader";

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
    },
    drawerPaper: {
        width: drawerWidth,
        borderRight: 'none'
    },
    drawerContainer: {
        overflow: 'auto',
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(3),
    },
    drawerSubtitle: {
        color: '#9f9f9f',
        marginBlockEnd: 0
    }
}));


export default function Layout(props) {
    const classes = useStyles();
    const [genres, setGenres] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const result = await API.get("/genres");
            setGenres(result.data);
        };

        fetchData();
    }, []);

    return (
        <div className={classes.root}>
            <CssBaseline />
            <AppBar position="fixed" className={classes.appBar}>
                <Toolbar>
                    <Typography variant="h6" noWrap>MovieVOD</Typography>
                </Toolbar>
            </AppBar>
            <Drawer
                className={classes.drawer}
                variant="permanent"
                classes={{
                    paper: classes.drawerPaper,
                }}
            >
                <Toolbar />
                <div className={classes.drawerContainer}>
                    <List>
                        <ListItem button component={NavLink} exact={true} to="/" >
                            <ListItemIcon><Movie style={{ color: colors.common.white }}/></ListItemIcon>
                            <ListItemText primary="Home" />
                        </ListItem>
                    </List>
                    <Divider />
                    <List>
                        <ListSubheader component="div" >
                            <h4 className={classes.drawerSubtitle}>Gatunki</h4>
                        </ListSubheader>
                        {genres.map((genre) => (
                            <ListItem button component={NavLink} to={"/gatunek/" + genre.name.toLowerCase()}  key={genre.name}>
                                <ListItemText primary={genre.name}/>
                            </ListItem>
                        ))}
                    </List>
                </div>
            </Drawer>
            <main className={classes.content}>
                <Toolbar />
                {props.children}
            </main>
        </div>
    );
}