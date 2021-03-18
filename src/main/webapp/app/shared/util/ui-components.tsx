import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import './ui-components.scss';
import { IconDefinition } from '@fortawesome/fontawesome-svg-core';

interface IHeadingProps {
  icon: string;
  heading: string;
}

export const Heading = (props: IHeadingProps) => {
  const { icon, heading } = props;
  return (
    <div className="ui-heading">
      <img src={icon} alt="heading-icon" />
      <h5>{heading}</h5>
    </div>
  );
};
