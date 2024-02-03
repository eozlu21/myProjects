using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.InputSystem;
public class Player : MonoBehaviour
{
    private Vector2 rawInput;
    [SerializeField] private float moveSpeed = 50f;
    private Vector2 minBounds;
    private Vector2 maxBounds;
    [SerializeField] private float paddingLeft;
    [SerializeField] private float paddingRight;
    [SerializeField] private float paddingTop;
    [SerializeField] private float paddingBottom;

    private void Start()
    {
        InitBounds();
    }

    void Update()
    {
        Move();
    }

    private void InitBounds()
    {
        Camera camera = Camera.main;
        minBounds = camera.ViewportToWorldPoint(new Vector2(0,0));
        maxBounds = camera.ViewportToWorldPoint(new Vector2(1,1));
    }
    private void Move()
    {
        Vector2 delta = rawInput * (moveSpeed * Time.deltaTime);
        Vector2 newPos = new Vector2();
        newPos.x = Mathf.Clamp(transform.position.x + delta.x ,minBounds.x + paddingLeft, maxBounds.x - paddingRight);
        newPos.y = Mathf.Clamp(transform.position.y + delta.y ,minBounds.y + paddingBottom, maxBounds.y - paddingTop);
        transform.position = newPos;

    }

    void OnMove(InputValue value)
    {
        rawInput = value.Get<Vector2>();
        Debug.Log(rawInput);
    }
}
