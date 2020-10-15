import React from 'react';
import PropTypes from 'prop-types';

import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Fab, Grid, IconButton } from '@material-ui/core';
import { AddRounded as CreateIcon, HelpOutlineRounded as HelpIcon } from '@material-ui/icons';

import { InterventionForm } from 'components';
import { CarPropType, ENTER_KEY_CODE, ESCAPE_KEY_CODE } from 'resources';

import './UpdateInterventionsModal.scss';

/**
 * The modal component to update the interventions of an existing car.
 *
 * @param {object} car
 *     The car to update
 * @param {string} [className = '']
 *     The component class name
 * @param {func} onClose
 *     The close event handler
 * @param {func} onEnter
 *     The enter event handler
 * @param {func} onUpdateCar
 *     The update car event handler
 * @param {func} onValidate
 *     The validate event handler
 * @param {boolean} open
 *     If the modal is open
 *
 * @constructor
 */
function UpdateInterventionsModal({ car, className, onClose, onEnter, onUpdateCar, onValidate, open }) {
    // Initializes the help flag
    const [ help, setHelp ] = React.useState(false);
    const [ expandedInterventionIndex, setExpandedInterventionIndex ] = React.useState(-1);

    /**
     * Handles the creation of an intervention.
     */
    const onCreateIntervention = () => {
        onUpdateCar({ interventions: [ ...car.interventions, { operations: [] } ], ...car });
    };

    /**
     * Handles the creation of an operation.
     */
    const onCreateOperation = x => {
        onUpdateCar({
            interventions:
                    car.interventions.map((intervention, i) => i === x ? {
                        operations: [ { operationLines: [] }, ...intervention.operations ], ...intervention
                    } : intervention)
            , ...car
        });
    };

    /**
     * Handles the creation of an operation line.
     */
    const onCreateOperationLine = (x, y) => {
        onUpdateCar({
            interventions: [
                car.interventions.map((intervention, i) => i === x ? {
                    operations: [
                        intervention.operations.map((operation, j) => j === y ? {
                            operationLines: [ ...operation.operationLines, { done: false } ], ...operation
                        } : operation)
                    ], ...intervention
                } : intervention)
            ], ...car
        });
    };

    /**
     * Handles the deletion of an intervention.
     *
     * @param x
     *     The index of the intervention to remove
     */
    const onDeleteIntervention = x => {
        onUpdateCar({ interventions: car.interventions.splice(x, 1), ...car });
    };

    /**
     * Handles the deletion of an operation.
     *
     * @param x
     *     The index of the intervention
     * @param y
     *     The index of the operation to remove
     */
    const onDeleteOperation = (x, y) => {
        onUpdateCar({
            interventions: [
                car.interventions.map((intervention, i) => i === x ? {
                    operations: intervention.operations.splice(y, 1), ...intervention
                } : intervention)
            ], ...car
        });
    };

    /**
     * Handles the deletion of an operation line.
     *
     * @param x
     *     The index of the intervention
     * @param y
     *     The index of the operation
     * @param z
     *     The index of the operation line to remove
     */
    const onDeleteOperationLine = (x, y, z) => {
        onUpdateCar({
            interventions: [
                car.interventions.map((intervention, i) => i === x ? {
                    operations: [
                        intervention.operations.map((operation, j) => j === y ? {
                            operationLines: operation.operationLines.splice(z, 1), ...operation
                        } : operation)
                    ]
                } : intervention)
            ], ...car
        });
    };

    /**
     * Handles the help button click action.
     */
    const onHelpButtonClick = () => {
        setHelp(!help);
    };

    /**
     * Handles the key pressed action.
     *
     * @param {object} e
     *     The event
     */
    const onKeyPressed = (e) => {
        switch (e.keyCode) {
            case ENTER_KEY_CODE:
                // Prevents the event from propagating
                e.preventDefault();
                onValidateAction();
                break;
            case ESCAPE_KEY_CODE:
                // Prevents the event from propagating
                e.preventDefault();
                onClose();
                break;
            default:
                break;
        }
    };

    /**
     * Handles the validation action.
     */
    const onValidateAction = () => {
        onClose();
    };

    return (<Dialog className={className} id='UpdateInterventionsModal' onClose={onClose} onEnter={onEnter} onKeyDown={onKeyPressed} open={open}>
        <DialogTitle className='Title'>
            Édition des interventions

            <IconButton className='HelpButton' color='primary' onClick={onHelpButtonClick}>
                <HelpIcon />
            </IconButton>
        </DialogTitle>

        <DialogContent>
            <DialogContentText className={help ? 'Instructions' : 'Instructions_hidden'}>
                <i>
                    {`Veuillez modifier les interventions de la voiture actuelle ci-dessous puis cliquer sur 'Valider'.
                    Les champs annotés du symbole * sont obligatoires.`}
                </i>
            </DialogContentText>

            {car.interventions.map((intervention, index) =>
                    <InterventionForm intervention={intervention} expanded={expandedInterventionIndex === index} key={index}
                                      onClick={() => setExpandedInterventionIndex(expandedInterventionIndex === index ? -1 : index)}
                                      onCreateOperation={() => onCreateOperation(index)}
                                      onCreateOperationLine={i => onCreateOperationLine(index, i)}
                                      onDeleteIntervention={() => onDeleteIntervention(index)}
                                      onDeleteOperation={i => onDeleteOperation(index, i)}
                                      onDeleteOperationLine={(i, j) => onDeleteOperationLine(index, i, j)} />
            )}
        </DialogContent>

        <DialogActions>
            <Grid alignItems='flex-end' container direction='row' justify='space-between'>
                <Grid item>
                    <Fab className='CreateInterventionButton' color='primary'
                         onClick={() => onUpdateCar({
                             ...car,
                             interventions: [ ...car.interventions, { operations: [] } ]
                         })} size='small'
                         variant='round'>
                        <CreateIcon className='CreateInterventionIcon' />
                    </Fab>
                </Grid>

                <Grid item>
                    <Button className='CancelButton' color='primary' onClick={onClose}>
                        Annuler
                    </Button>

                    <Button autoFocus className='ValidateButton' color='secondary' onClick={onCreateIntervention}>
                        Valider
                    </Button>
                </Grid>
            </Grid>
        </DialogActions>
    </Dialog>);
}

UpdateInterventionsModal.propTypes = {
    car: CarPropType.isRequired,
    className: PropTypes.string,
    open: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
    onValidate: PropTypes.func.isRequired
};

UpdateInterventionsModal.defaultProps = {
    className: ''
};

export default UpdateInterventionsModal;
