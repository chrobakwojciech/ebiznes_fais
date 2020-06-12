import React from "react";
import Box from "@material-ui/core/Box";
import makeStyles from "@material-ui/core/styles/makeStyles";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import {Link} from "react-router-dom";


const useStyles = makeStyles((theme) => ({
    fullScreen: {
        backgroundColor: 'white',
        height: '100%',
        width: '100%'
    },
    travolta: {
        width: '100%',
        display: 'block'
    },
    error: {
        color: theme.palette.background.default,
        textAlign: 'center'
    },
    statusCode: {
        fontSize: '14rem'
    }
}));


export default function NotFound() {

    const classes = useStyles();
    return (
        <>
            <Box display="flex" justifyContent="center" className={classes.fullScreen}>
                <Box className={classes.error} width="60%">
                    <Box display="flex" alignItems="center" justifyContent="center" css={{ height: '100%' }}>
                        <Box>
                            <Typography className={classes.statusCode} variant="h1" component="h2">
                                404
                            </Typography>
                            <Typography variant="h5" component="h4" gutterBottom>
                                Nie ma takiej strony
                            </Typography>

                            <Button component={Link} to="/" variant="outlined" color="primary">
                                Strona główna
                            </Button>
                        </Box>
                    </Box>

                </Box>
                <Box width="40%" alignSelf="flex-end">
                    <img className={classes.travolta} src="/404.gif" alt={"travolta"}/>
                </Box>


            </Box>
        </>
    )
}