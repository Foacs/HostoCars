import {
    Box,
    Button,
    CircularProgress,
    ExpansionPanel,
    ExpansionPanelActions,
    ExpansionPanelDetails,
    ExpansionPanelSummary,
    Grid,
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableRow,
    Typography
} from '@material-ui/core';
import { ErrorOutlineRounded as ErrorIcon, SearchRounded as DisplayIcon, SentimentDissatisfiedRounded as SmileyIcon } from '@material-ui/icons';
import { changeCurrentPageAction, changeSelectedMenuIndexAction, getCarAction } from 'actions';
import { CertificateModal } from 'modals';
import PropTypes from 'prop-types';
import React, { Fragment, PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { CarPropType, DefaultCarPicture, formatDateLabel } from 'resources';

import './CarPage.scss';

class CarPage extends PureComponent {
    constructor(props) {
        super(props);

        this.state = { certificateModalOpen: false };

        this.onOpenCertificateModal = this.onOpenCertificateModal.bind(this);
        this.onCloseAddCarModal = this.onCloseAddCarModal.bind(this);
    }

    componentDidMount() {
        const { changeSelectedMenuIndex, currentCar, getCar, match: { params: { id } } } = this.props;

        getCar(Number(id));

        changeSelectedMenuIndex(0);

        this.updateBreadcrumbs = this.updateBreadcrumbs.bind(this);
        this.updateBreadcrumbs(currentCar);
    }

    componentDidUpdate(prevProps, prevState) {
        const { currentCar: previousCar } = prevProps;
        const { currentCar } = this.props;

        if (previousCar !== currentCar) {
            this.updateBreadcrumbs(currentCar);
        }
    }

    updateBreadcrumbs(car) {
        const { changeCurrentPage, isGetInError, isGetInProgress } = this.props;

        let content = '';
        if (isGetInProgress) {
            content = <CircularProgress size={20} thickness={4} />;
        } else if (isGetInError) {
            content = <ErrorIcon />;
        } else if (null === car) {
            content = <SmileyIcon />;
        } else {
            content = car.registration;
        }

        changeCurrentPage(content, [ {
            label: 'Voitures',
            link: '/cars'
        } ]);
    }

    onOpenCertificateModal() {
        this.setState({ certificateModalOpen: true });
    };

    onCloseAddCarModal() {
        this.setState({ certificateModalOpen: false });
    };

    render() {
        const { currentCar: car } = this.props;
        const { certificateModalOpen } = this.state;

        let content;
        if (null === car) {

        } else {
            const picture = car.picture ? <img alt={`Car n°${car.id}`} className='PicturePanel-Picture'
                                               src={`data:image/jpeg;base64,${car.picture}`} /> : <DefaultCarPicture
                className='PicturePanel-Picture PicturePanel-Picture_default' />;

            const certificateButton = <IconButton className="InfoPanel-Content-Table-Body-Row-Cell-Button" onClick={this.onOpenCertificateModal}>
                <DisplayIcon className="InfoPanel-Content-Table-Body-Row-Cell-Button-Icon" />
            </IconButton>;

            const commentsSection = <Fragment><Grid item>
                <Typography className='InfoPanel-Content-CommentsSubtitle' variant='subtitle1'>
                    Commentaires
                </Typography>
            </Grid>

                <Grid item>
                    <Typography className='InfoPanel-Content-Comments' variant='body2'>
                        {car.comments}
                    </Typography>
                </Grid>
            </Fragment>;

            content = <Grid container spacing={4}>
                <Grid container item xs={6}>
                    <Grid item xs={12}>
                        <ExpansionPanel className='InfoPanel' defaultExpanded>
                            <ExpansionPanelSummary className='InfoPanel-Header'>
                                <Typography className='InfoPanel-Header-Title' color='primary' variant='h6'>
                                    Informations
                                </Typography>
                            </ExpansionPanelSummary>

                            <ExpansionPanelDetails className='InfoPanel-Content'>
                                <Grid container direction='column' spacing={2}>
                                    <Grid item>
                                        <Table className='InfoPanel-Content-Table'>
                                            <TableBody className='InfoPanel-Content-Table-Body'>
                                                <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                    <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        Propriétaire
                                                    </TableCell>

                                                    <TableCell align='right' className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        {car.owner}
                                                    </TableCell>
                                                </TableRow>

                                                <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                    <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        Numéro d'immatriculation
                                                    </TableCell>

                                                    <TableCell align='right' className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        {car.registration}
                                                    </TableCell>
                                                </TableRow>

                                                <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                    <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        Marque
                                                    </TableCell>

                                                    <TableCell align='right' className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        {car.brand}
                                                    </TableCell>
                                                </TableRow>

                                                <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                    <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        Modèle
                                                    </TableCell>

                                                    <TableCell align='right' className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        {car.model}
                                                    </TableCell>
                                                </TableRow>

                                                <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                    <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        Motorisation
                                                    </TableCell>

                                                    <TableCell align='right' className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        {car.motorization}
                                                    </TableCell>
                                                </TableRow>

                                                <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                    <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        Date de mise en circulation
                                                    </TableCell>

                                                    <TableCell align='right' className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        {formatDateLabel(car.releaseDate)}
                                                    </TableCell>
                                                </TableRow>

                                                <TableRow className='InfoPanel-Content-Table-Body-Row' hover>
                                                    <TableCell className='InfoPanel-Content-Table-Body-Row-Cell'>
                                                        Carte grise
                                                    </TableCell>

                                                    <TableCell align='right'
                                                               className='InfoPanel-Content-Table-Body-Row-Cell InfoPanel-Content-Table-Body-Row-Cell_certificate'>
                                                        {car.certificate && certificateButton}
                                                    </TableCell>
                                                </TableRow>
                                            </TableBody>
                                        </Table>
                                    </Grid>

                                    {car.comments && commentsSection}
                                </Grid>
                            </ExpansionPanelDetails>

                            <ExpansionPanelActions className='InfoPanel-Actions'>
                                <Button className='InfoPanel-Actions-EditButton' color='primary'>
                                    Éditer
                                </Button>
                            </ExpansionPanelActions>
                        </ExpansionPanel>
                    </Grid>
                </Grid>

                <Grid container item xs={6}>
                    <Grid item xs={12}>
                        <Paper className='PicturePanel'>
                            {picture}
                        </Paper>
                    </Grid>
                </Grid>
            </Grid>;
        }

        return <Box id="CarPage">
            {content}

            <CertificateModal className='CertificateModal' onClose={this.onCloseAddCarModal} open={certificateModalOpen}
                              certificate={car && car.certificate} />
        </Box>;
    }
}

const mapStateToProps = state => ({
    currentCar: state.cars.currentCar,
    isGetInError: state.cars.isGetInError,
    isGetInProgress: state.cars.isGetInProgress
});

const mapDispatchToProps = dispatch => bindActionCreators({
    changeCurrentPage: changeCurrentPageAction,
    changeSelectedMenuIndex: changeSelectedMenuIndexAction,
    getCar: getCarAction
}, dispatch);

CarPage.propTypes = {
    currentCar: CarPropType,
    changeCurrentPage: PropTypes.func.isRequired,
    changeSelectedMenuIndex: PropTypes.func.isRequired,
    getCar: PropTypes.func.isRequired,
    isGetInError: PropTypes.bool.isRequired,
    isGetInProgress: PropTypes.bool.isRequired,
    match: PropTypes.shape({
        params: PropTypes.shape({
            id: PropTypes.string.isRequired
        }).isRequired
    }).isRequired
};

CarPage.defaultProps = {
    currentCar: null
};

export default connect(mapStateToProps, mapDispatchToProps)(CarPage);
