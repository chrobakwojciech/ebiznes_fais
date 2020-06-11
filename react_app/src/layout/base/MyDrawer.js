import Toolbar from "@material-ui/core/Toolbar";
import {NavLink} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {makeStyles} from "@material-ui/core/styles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon/ListItemIcon";
import {Movie} from "@material-ui/icons";
import * as colors from "@material-ui/core/colors";
import ListItemText from "@material-ui/core/ListItemText/ListItemText";
import Divider from "@material-ui/core/Divider";
import ListSubheader from "@material-ui/core/ListSubheader";
import Drawer from "@material-ui/core/Drawer/Drawer";
import {genreApi} from "../../utils/api/genre.api";

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
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
    drawerSubtitle: {
        color: '#9f9f9f',
        marginBlockEnd: 0
    },
    title: {
        flexGrow: 1
    }
}));


export default function MyDrawer() {
    const classes = useStyles();
    const [genres, setGenres] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const genres = await genreApi.getAll();
            setGenres(genres);
        };

        fetchData();
    }, []);

    return (
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
    )
}