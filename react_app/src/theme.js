import {createMuiTheme} from '@material-ui/core/styles';
import * as colors from '@material-ui/core/colors'

const theme = createMuiTheme({
    typography: {
        fontFamily: [
            'Catamaran'
        ],
        h5: {
            fontWeight: "bold"
        }
    },
    palette: {
        primary: {
            main: colors.indigo["900"],
        },
        secondary: {
            main: '#1de2ad',
        },
        text: {
            primary: '#fff',
        },
        background: {
            default: colors.common.white,
            paper: '#111'
        },
    },
});

// theme.shadows = [];
export default theme;