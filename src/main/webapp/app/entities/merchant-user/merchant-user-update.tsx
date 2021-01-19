import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IMerchant } from 'app/shared/model/merchant.model';
import { getEntities as getMerchants } from 'app/entities/merchant/merchant.reducer';
import { getEntity, updateEntity, createEntity, reset } from './merchant-user.reducer';
import { IMerchantUser } from 'app/shared/model/merchant-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMerchantUserUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MerchantUserUpdate = (props: IMerchantUserUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [merchantId, setMerchantId] = useState('0');
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { merchantUserEntity, users, merchants, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/merchant-user');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getMerchants();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...merchantUserEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="btcPaymentSystemApp.merchantUser.home.createOrEditLabel" data-cy="MerchantUserCreateUpdateHeading">
            Create or edit a MerchantUser
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : merchantUserEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="merchant-user-id">ID</Label>
                  <AvInput id="merchant-user-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="merchant-user-user">User</Label>
                <AvInput id="merchant-user-user" data-cy="user" type="select" className="form-control" name="user.id">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="merchant-user-merchant">Merchant</Label>
                <AvInput id="merchant-user-merchant" data-cy="merchant" type="select" className="form-control" name="merchant.id">
                  <option value="" key="0" />
                  {merchants
                    ? merchants.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/merchant-user" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  merchants: storeState.merchant.entities,
  merchantUserEntity: storeState.merchantUser.entity,
  loading: storeState.merchantUser.loading,
  updating: storeState.merchantUser.updating,
  updateSuccess: storeState.merchantUser.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getMerchants,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MerchantUserUpdate);
