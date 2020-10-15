import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import {
    Box, Chip, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Grid, Table, TableBody, TableCell, TableRow, Typography
} from '@material-ui/core';
import { RefreshRounded as RefreshIcon } from '@material-ui/icons';

import { getCarsAction, updateCurrentPageAction, updateMenuItemsAction, updateSelectedMenuIndexAction } from 'actions';
import { ErrorPanel, InterventionPreview, LoadingPanel, Page } from 'components';
import { INTERVENTION_STATUS_STEPS, InterventionPropType } from '../../../resources';

import './InterventionsOverviewPage.scss';

/**
 * The interventions overview page component.
 *
 * @param {object[]} interventions
 *    The list of all the interventions
 * @param {boolean} isInError
 *    If the interventions loading is in error
 * @param {boolean} isLoading
 *    If the interventions loading is in progress
 * @param {func} updateCurrentPage
 *     The {@link updateCurrentPageAction} action
 * @param {func} updateSelectedMenuIndex
 *     The {@link updateSelectedMenuIndexAction} action
 *
 * @class
 */
class InterventionsOverviewPage extends PureComponent {
    /**
     * Constructor.
     *
     * @param {object} props
     *     The component props
     *
     * @constructor
     */
    constructor(props) {
        super(props);

        // Initializes the component state
        this.state = { expandedInterventionIndex: false };

        // Binds the local method
        this.onInterventionPreviewClick = this.onInterventionPreviewClick.bind(this);
    }

    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const { getCars, updateCurrentPage, updateMenuItems, updateSelectedMenuIndex } = this.props;

        updateCurrentPage('Interventions', []);
        updateMenuItems([ {
            icon: <RefreshIcon />,
            label: 'Rafraîchir',
            onClick: getCars
        } ]);
        updateSelectedMenuIndex(1);
    }

    /**
     * Handles an intervention preview panel click action.
     * <br/>
     * This method is used to create an accordion effect on the interventions previews.
     *
     * @param index
     *    The index of the expanded intervention preview panel
     */
    onInterventionPreviewClick(index) {
        this.setState({ expandedInterventionIndex: this.state.expandedInterventionIndex === index ? false : index });
    }

    /**
     * Render method.
     */
    render() {
        const { interventions, isInError, isLoading } = this.props;
        const { expandedInterventionIndex } = this.state;

        let content;
        if (isInError) {
            // If the interventions or the cars failed to be loaded, displays the error panel
            content = (<ErrorPanel className='ErrorPanel' />);
        } else if (isLoading) {
            // If the interventions or the cars are being loaded, displays the loading panel
            content = (<LoadingPanel className='LoadingPanel' />);
        } else {
            const interventionsNumber = interventions.length;
            const finishedInterventionsNumber = interventions.filter(intervention => intervention.status === INTERVENTION_STATUS_STEPS[4]).length;
            const areAllInterventionsFinished = interventionsNumber === finishedInterventionsNumber;

            const operations = interventions.flatMap(intervention => intervention.operations);
            const operationsNumber = operations.length;
            const finishedOperationsNumber = operations.filter(operation => !operation.operationLines.some(line => !line.done)).length;
            const areAllOperationsFinished = operationsNumber === finishedOperationsNumber;

            const operationLines = operations.flatMap(operation => operation.operationLines);
            const operationLinesNumber = operationLines.length;
            const finishedOperationLinesNumber = operationLines.filter(operationLine => !operationLine.done).length;
            const areAllOperationLinesFinished = operationLinesNumber === finishedOperationLinesNumber;

            // If the interventions and the cars have been loaded, displays the page normal content
            content = (<Grid container spacing={4}>
                <Grid item xs={4}>
                    <Box className='StaticColumn'>
                        <ExpansionPanel className='OverviewInfoPanel' expanded={true}>
                            <ExpansionPanelSummary className='Header'>
                                <Typography className='Title' color='primary' variant='h6'>Statistiques</Typography>
                            </ExpansionPanelSummary>

                            <ExpansionPanelDetails>
                                <Table className='Table'>
                                    <TableBody>
                                        <TableRow className='TableRow' hover>
                                            <TableCell className='TableRowLabel'>Interventions</TableCell>
                                            <TableCell align='right' className='TableRowValue'>
                                                <Chip className='TableRowValue'
                                                      color={areAllInterventionsFinished ? 'secondary' : 'primary'}
                                                      label={`${finishedInterventionsNumber} ⋮ ${interventionsNumber}`} size='small'
                                                      variant={areAllInterventionsFinished ? 'outlined' : 'default'} />
                                            </TableCell>
                                        </TableRow>

                                        <TableRow className='TableRow' hover>
                                            <TableCell className='TableRowLabel'>Opérations</TableCell>
                                            <TableCell align='right' className='TableRowValue'>
                                                <Chip className='TableRowValue'
                                                      color={areAllOperationsFinished ? 'secondary' : 'primary'}
                                                      label={`${finishedOperationsNumber} ⋮ ${operationsNumber}`} size='small'
                                                      variant={areAllOperationsFinished ? 'outlined' : 'default'} />
                                            </TableCell>
                                        </TableRow>

                                        <TableRow className='TableRow' hover>
                                            <TableCell className='TableRowLabel'>Lignes d'opération</TableCell>
                                            <TableCell align='right' className='TableRowValue'>
                                                <Chip className='TableRowValue'
                                                      color={areAllOperationLinesFinished ? 'secondary' : 'primary'}
                                                      label={`${finishedOperationLinesNumber} ⋮ ${operationLinesNumber}`} size='small'
                                                      variant={areAllOperationLinesFinished ? 'outlined' : 'default'} />
                                            </TableCell>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </ExpansionPanelDetails>
                        </ExpansionPanel>
                    </Box>
                </Grid>

                <Grid item xs={8}>
                    {interventions.map((intervention, index) =>
                            <InterventionPreview intervention={intervention} expanded={expandedInterventionIndex === index} key={index}
                                                 onClick={() => this.onInterventionPreviewClick(index)} />)}
                </Grid>
            </Grid>);
        }

        return (<Page id='InterventionsOverviewPage'>
            {content}
        </Page>);
    }
}

const mapStateToProps = (state) => ({
    interventions: state.cars.cars
            .flatMap(car => car.interventions.map(intervention => {
                return {
                    ...intervention,
                    carId: car.id,
                    carRegistration: car.registration
                };
            })),
    isInError: state.cars.isGetInError,
    isLoading: state.cars.isGetInProgress
});

const mapDispatchToProps = (dispatch) => bindActionCreators({
    getCars: getCarsAction,
    updateCurrentPage: updateCurrentPageAction,
    updateMenuItems: updateMenuItemsAction,
    updateSelectedMenuIndex: updateSelectedMenuIndexAction
}, dispatch);

InterventionsOverviewPage.propTypes = {
    interventions: PropTypes.arrayOf(InterventionPropType).isRequired,
    isInError: PropTypes.bool.isRequired,
    isLoading: PropTypes.bool.isRequired,
    updateCurrentPage: PropTypes.func.isRequired,
    updateMenuItems: PropTypes.func.isRequired,
    updateSelectedMenuIndex: PropTypes.func.isRequired
};

export default connect(mapStateToProps, mapDispatchToProps)(InterventionsOverviewPage);
