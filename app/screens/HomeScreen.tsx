import { Button } from '@react-navigation/elements';
import React from 'react';
import { StyleSheet, View } from 'react-native';

type Props = {};

const pwaPocUrl =
  'https://wwwsit.aia.com.hk/content/dam/hk/iChange/poc/pwa/index.html';

export default function HomeScreen({ }: Props) {

  const handleScan = () => {
    console.log('scan');
  }

  return (
    <View style={styles.container}>
      <Button screen="WebviewScreen" params={{ url: pwaPocUrl }}>
        PWA POC
      </Button>
      <Button screen="OpenLink">Open a link</Button>
      <Button onPress={handleScan}>Open mpaas Scan</Button>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    gap: 20,
  },
  button: {
    padding: 20,
    fontSize: 30,
  },
});
