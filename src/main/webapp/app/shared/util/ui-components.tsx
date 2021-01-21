import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import './ui-components.scss';
import { IconDefinition } from '@fortawesome/fontawesome-svg-core';

interface IHeadingProps {
  icon: IconDefinition;
  heading: string;
}

export const Heading = (props: IHeadingProps) => {
  const { icon, heading } = props;
  return (
    <div className="ui-heading">
      <FontAwesomeIcon icon={icon} size={'lg'} />
      <h5>{heading}</h5>
    </div>
  );
};
