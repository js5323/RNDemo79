import React from 'react';

import type {StaticScreenProps} from '@react-navigation/native';
import WebViewWithCustomRefresh from '../components/WebViewWithCustomRefresh';

type Props = StaticScreenProps<{
  url: string;
}>;

export default function WebviewScreen({route}: Props) {
  return <WebViewWithCustomRefresh uri={route.params.url} />;
}
