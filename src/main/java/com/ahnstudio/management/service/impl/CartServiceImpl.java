package com.ahnstudio.management.service.impl;

import com.ahnstudio.management.common.Const;
import com.ahnstudio.management.common.ServerResponse;
import com.ahnstudio.management.dao.CartMapper;
import com.ahnstudio.management.dao.ProductMapper;
import com.ahnstudio.management.pojo.Cart;
import com.ahnstudio.management.pojo.Product;
import com.ahnstudio.management.service.CartService;
import com.ahnstudio.management.vo.CartProductVO;
import com.ahnstudio.management.vo.CartVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    public ServerResponse<CartVO> add(HttpSession session, Integer userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorMessage(Const.Message.PARAMETER_ERROR);
        }

        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart == null) {
            // 해당상품은 장바구니에 없음.  새로 추가해야됨
            Cart cartItem = new Cart();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQty(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(cartItem);
        } else {
            // 해당상품 이미 장바구니에 있음, 수량업데이트해줘야됨.
            count = cart.getQty() + count;
            cart.setQty(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }

        return this.list(userId);
    }

    /**
     * user의 장바구니 반환
     * @param userId
     * @return
     */
    public ServerResponse<CartVO> list (Integer userId){
        CartVO cartVo = this.getCartVOLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    private CartVO getCartVOLimit(Integer userId) {
        CartVO cartVO = new CartVO();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVO> cartProductVOList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(cartList)) {
            for (Cart cartItem : cartList) {
                // 1. cart에 담길 제품세팅함.
                CartProductVO cartProductVO = new CartProductVO();
                cartProductVO.setId(cartItem.getId());
                cartProductVO.setUserId(userId);
                cartProductVO.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null) {
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    cartProductVO.setProductStatus(product.getStatus()); //제품상태 (上架/下架)
                    cartProductVO.setProductPrice(product.getPrice()); // 제품 가격
                    cartProductVO.setProductStock(product.getStock());

                    //判断库存
                    int buyLimitCount = 0;


                }
            }
        }




        return null;
    }

}
