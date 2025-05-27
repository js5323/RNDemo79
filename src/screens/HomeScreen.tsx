import { Button } from '@react-navigation/elements';
import React, { useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import RTNCalculator from "rtn-calculator/js/NativeRTNCalculator";

type Props = {};

const pwaPocUrl =
  'https://wwwsit.aia.com.hk/content/dam/hk/iChange/poc/pwa/index.html';

export default function HomeScreen({ }: Props) {

  const [result, setResult] = useState<number>()
  return (
    <View style={styles.container}>
      <Button screen="WebviewScreen" params={{ url: pwaPocUrl }}>
        PWA POC
      </Button>
      <Button screen="OpenLink">Open a link</Button>

      <Text style={{ marginLeft: 20, marginTop: 20 }}>
        3+7={result ?? "??"}
      </Text>
      <Button
        onPress={async () => {
          const value = await RTNCalculator?.add(3, 7);
          setResult(value);
        }}
      >Compute</Button>
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
