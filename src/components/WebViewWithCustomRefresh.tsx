import React, {useState, useRef, useEffect} from 'react';
import {
  StyleSheet,
  View,
  Text,
  Animated,
  PanResponder,
  // Dimensions,
  ActivityIndicator,
} from 'react-native';
import WebView, {WebViewProps} from 'react-native-webview';

// const {height} = Dimensions.get('window');

type WebViewWithCustomRefreshProps = {
  uri: string;
};

const WebViewWithCustomRefresh = ({uri}: WebViewWithCustomRefreshProps) => {
  const [refreshing, setRefreshing] = useState(false);
  const [pullDistance, setPullDistance] = useState(0);
  const [isAtTop, setIsAtTop] = useState(true); // 标记 WebView 是否在顶部
  const webViewRef = useRef<WebView>(null);
  const scrollY = useRef(new Animated.Value(0)).current;
  const pullThreshold = 80; // 触发刷新的下拉距离
  const debounceTimer = useRef<NodeJS.Timeout | null>(null); // 防抖计时器

  // 禁用网页原生下拉刷新的脚本
  const disablePullToRefreshScript = `
    // 禁用网页原生下拉刷新
    document.addEventListener('touchmove', function(e) {
      if (window.scrollY === 0) {
        e.preventDefault();
      }
    }, { passive: false });

    // 监听滚动位置并通知 RN
    window.addEventListener('scroll', function() {
      const isAtTop = window.scrollY === 0;
      window.ReactNativeWebView.postMessage(JSON.stringify({
        type: 'scroll',
        isAtTop: isAtTop
      }));
    });

    // 初始加载完成后发送一次位置信息
    setTimeout(() => {
      window.ReactNativeWebView.postMessage(JSON.stringify({
        type: 'scroll',
        isAtTop: window.scrollY === 0
      }));
    }, 500);
  `;

  // 处理下拉手势
  const panResponder = useRef(
    PanResponder.create({
      onMoveShouldSetPanResponder: (_, gestureState) => {
        // 仅在页面顶部且向下拖动时响应
        return gestureState.dy > 0 && isAtTop && pullDistance === 0;
      },
      onPanResponderMove: (_, gestureState) => {
        if (!refreshing) {
          const distance = Math.min(gestureState.dy, pullThreshold * 2);
          setPullDistance(distance);
          scrollY.setValue(distance);
        }
      },
      onPanResponderRelease: (_, gestureState) => {
        if (gestureState.dy > pullThreshold) {
          // 防抖处理：避免短时间内多次触发刷新
          if (debounceTimer.current) clearTimeout(debounceTimer.current);
          debounceTimer.current = setTimeout(() => {
            setRefreshing(true);
            webViewRef.current?.reload();
            setTimeout(() => {
              setRefreshing(false);
              setPullDistance(0);
              scrollY.setValue(0);
            }, 2000); // 模拟加载时间
          }, 300);
        } else {
          // 未达到阈值，重置
          Animated.timing(scrollY, {
            toValue: 0,
            duration: 300,
            useNativeDriver: false,
          }).start(() => setPullDistance(0));
        }
      },
    }),
  ).current;

  // 刷新状态变化时的动画
  useEffect(() => {
    if (refreshing) {
      Animated.timing(scrollY, {
        toValue: pullThreshold,
        duration: 300,
        useNativeDriver: false,
      }).start();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [refreshing]);

  // 处理来自 WebView 的消息
  const handleMessage: WebViewProps['onMessage'] = event => {
    try {
      const data = JSON.parse(event.nativeEvent.data);
      if (data.type === 'scroll') {
        setIsAtTop(data.isAtTop);
      }
    } catch (e) {
      console.log('Message parse error:', e);
    }
  };

  return (
    <View style={styles.container} {...panResponder.panHandlers}>
      {/* 自定义刷新指示器 */}
      <Animated.View
        style={[styles.refreshIndicator, {transform: [{translateY: scrollY}]}]}>
        {refreshing ? (
          <View style={styles.refreshingContainer}>
            <ActivityIndicator size="small" color="#007AFF" />
            <Text style={styles.refreshText}>正在刷新...</Text>
          </View>
        ) : pullDistance > pullThreshold / 2 ? (
          <Text style={styles.refreshText}>释放刷新</Text>
        ) : (
          <Text style={styles.refreshText}>下拉刷新</Text>
        )}
      </Animated.View>

      <WebView
        ref={webViewRef}
        source={{uri}}
        style={[styles.webView, {marginTop: pullDistance}]}
        javaScriptEnabled={true}
        injectedJavaScript={disablePullToRefreshScript}
        onMessage={handleMessage}
        startInLoadingState={true} // 显示加载状态
        cacheEnabled={true} // 启用缓存
        mixedContentMode="compatibility" // 混合内容模式
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
  },
  webView: {
    flex: 1,
    width: '100%',
  },
  refreshIndicator: {
    height: 60,
    width: '100%',
    justifyContent: 'center',
    alignItems: 'center',
    position: 'absolute',
    top: -60,
    backgroundColor: '#f5f5f5',
  },
  refreshingContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  refreshText: {
    fontSize: 14,
    color: '#333',
    marginLeft: 8,
  },
});

export default WebViewWithCustomRefresh;
